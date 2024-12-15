package com.deliveryManPlus.auth.service.imp;

import com.deliveryManPlus.auth.constant.UserPolicy;
import com.deliveryManPlus.auth.model.dto.Authentication;
import com.deliveryManPlus.auth.model.dto.LeaveRequestDto;
import com.deliveryManPlus.auth.model.dto.LoginRequestDto;
import com.deliveryManPlus.auth.model.dto.SigninRequestDto;
import com.deliveryManPlus.auth.model.entity.BasicAuth;
import com.deliveryManPlus.auth.repository.AuthRepository;
import com.deliveryManPlus.auth.service.AuthService;
import com.deliveryManPlus.auth.utils.PasswordEncoder;
import com.deliveryManPlus.common.exception.constant.AuthErrorCode;
import com.deliveryManPlus.common.exception.exception.ApiException;
import com.deliveryManPlus.user.constant.Status;
import com.deliveryManPlus.user.model.entity.User;
import com.deliveryManPlus.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;

@Transactional
@Service
@RequiredArgsConstructor
public class authServiceImp implements AuthService {

    private final AuthRepository authRepository;
    private final UserRepository userRepository;

    @Override
    public void signin(SigninRequestDto dto) {
        //검증
        Optional<BasicAuth> authByEmail = authRepository.findByEmail(dto.getEmail());
        if(authByEmail.isPresent()){
            User user = authByEmail.get().getUser();
            if(user.getStatus().equals(Status.USE)){
                throw new ApiException(AuthErrorCode.EXIST_USER);
            }
            if(!(user.getStatus().equals(Status.CANCEL) && isCorrectPolicy(user))){
                throw new ApiException(AuthErrorCode.CANCELED_USER);
            }
        }

        //user 저장
        User user = dto.toUserEntity();
        userRepository.save(user);

        //basicAuth 저장
        BasicAuth basicAuth = dto.toBasicAuthEntity();
        basicAuth.updateUser(user);

        authRepository.save(basicAuth);

    }

    private boolean isCorrectPolicy(User user) {
        return user.getCanceledDate().isBefore(LocalDate.now().minusMonths(UserPolicy.RESIGNIN_MONTHS.getMonths()));
    }

    @Override
    public Authentication login(LoginRequestDto dto) {
        //검증
        BasicAuth basicAuth = authRepository.findByEmail(dto.getEmail())
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE));

        if (!PasswordEncoder.matches(dto.getPassword(), basicAuth.getPassword())) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        }

        return new Authentication(basicAuth);
    }

    @Override
    public void leave(LeaveRequestDto dto, Authentication authentication) {
        //검증
        User user = userRepository.findById(authentication.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST));

        if (!PasswordEncoder.matches(dto.getPassword(),user.getBasicAuthList().get(0).getPassword())) {
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE);
        }

        user.setStatus(Status.CANCEL);
    }
}
