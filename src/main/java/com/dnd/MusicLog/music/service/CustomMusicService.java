package com.dnd.MusicLog.music.service;

import com.dnd.MusicLog.global.error.exception.BusinessLogicException;
import com.dnd.MusicLog.global.error.exception.ErrorCode;
import com.dnd.MusicLog.music.dto.CustomMusicItem;
import com.dnd.MusicLog.music.dto.CustomMusicRequestDto;
import com.dnd.MusicLog.music.dto.CustomMusicResponseDto;
import com.dnd.MusicLog.music.dto.SearchCustomMusicResponseDto;
import com.dnd.MusicLog.music.entity.custom.CustomMusic;
import com.dnd.MusicLog.music.repository.custom.CustomMusicRepository;
import com.dnd.MusicLog.user.entity.User;
import com.dnd.MusicLog.user.service.OAuthLoginService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CustomMusicService {

    private final OAuthLoginService oAuthLoginService;
    private final CustomMusicRepository customMusicRepository;

    @Transactional(readOnly = true)
    public SearchCustomMusicResponseDto searchCustomMusic(long userId, String query, int offset, int size) {
        PageRequest pageRequest = PageRequest.of(offset, size);

        List<CustomMusic> customMusic =
            customMusicRepository.searchAllByUserIdAndQuery(userId, query, pageRequest);

        List<CustomMusicItem> items = customMusic.stream()
            .map(CustomMusicItem::new)
            .collect(Collectors.toList());

        return new SearchCustomMusicResponseDto(offset, customMusic.size(), items);
    }

    @Transactional(readOnly = true)
    public CustomMusicItem searchCustomMusic(long userId, long customMusicId) {
        User user = oAuthLoginService.getUser(userId);

        CustomMusic music = customMusicRepository.findByIdAndAuthor(customMusicId, user)
            .orElseThrow(() -> new BusinessLogicException(ErrorCode.NOT_FOUND));

        return new CustomMusicItem(music);
    }

    @Transactional
    public CustomMusicResponseDto saveCustomMusic(long userId, CustomMusicRequestDto requestDto) {
        User user = oAuthLoginService.getUser(userId);

        CustomMusic customMusic = CustomMusic.builder()
            .name(requestDto.name())
            .artist(requestDto.artist())
            .imageUrl(requestDto.imageUrl())
            .author(user)
            .build();

        customMusicRepository.save(customMusic);

        return new CustomMusicResponseDto(customMusic);
    }

    @Transactional
    public CustomMusicResponseDto updateCustomMusic(
        long userId,
        long customMusicId,
        CustomMusicRequestDto request) {
        User user = oAuthLoginService.getUser(userId);

        CustomMusic music = customMusicRepository.findByIdAndAuthor(customMusicId, user)
            .orElseThrow(() -> new BusinessLogicException(ErrorCode.NOT_FOUND));

        music.updateStaticInfo(request.name(), request.artist(), request.imageUrl());

        return new CustomMusicResponseDto(music);
    }

    @Transactional
    public CustomMusicResponseDto deleteCustomMusic(long userId, long customMusicId) {
        User user = oAuthLoginService.getUser(userId);

        CustomMusic music = customMusicRepository.findByIdAndAuthor(customMusicId, user)
            .orElseThrow(() -> new BusinessLogicException(ErrorCode.NOT_FOUND));

        CustomMusicResponseDto response = new CustomMusicResponseDto(music);

        customMusicRepository.delete(music);

        return response;
    }
}
