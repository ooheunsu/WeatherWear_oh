package com.spring.WeatherWear.member.service;

import com.spring.WeatherWear.member.dto.LoginRequest;
import com.spring.WeatherWear.member.dto.SignUpRequest;
import com.spring.WeatherWear.member.entity.Member;
import com.spring.WeatherWear.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final GenerateNickName generateNickName;

    public void signUp(SignUpRequest signUpRequest) {
        if (isDuplicateEmail(signUpRequest.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }
        Member member = signUpRequest.toEntity(generateNickName.generateName());
        memberRepository.save(member);
    }

    public void updateMember(Member member) {
        memberRepository.save(member);
    }

    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }

    public boolean isDuplicateEmail(String email) {
        return memberRepository.findByEmail(email).isPresent();
    }

    public Member login(LoginRequest loginRequest) {
        Optional<Member> byMemberEmail = memberRepository.findByEmail(loginRequest.getEmail());

        if (byMemberEmail.isPresent()) {
            Member member = byMemberEmail.get();
            if (member.getPassword().equals(loginRequest.getPassword())) {
                return member;
            }
        }

        return null;
    }
}
