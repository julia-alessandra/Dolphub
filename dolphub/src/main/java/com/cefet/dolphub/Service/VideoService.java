package com.cefet.dolphub.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cefet.dolphub.Entidades.Recursos.Video;
import com.cefet.dolphub.Repositorio.VideoRepository;

@Service
public class VideoService {
    @Autowired
    private VideoRepository videoRepository;

    public VideoService() {
    }

    public Video salvarVideo(Video video) {
        return videoRepository.save(video);
    }

    public Video buscar(Long id) {
        Optional<Video> video = videoRepository.findById(id);
        return video.orElseThrow(() -> new RuntimeException("Vídeo não encontrado!"));
    }

    public Video encontrarVideoPorId(Long id) {
        return videoRepository.findById(id).orElse(null);
    }

    public void deletar(Long id) {
        videoRepository.deleteById(id);
    }

    public List<Video> listarVideo() {
        return videoRepository.findAll();
    }

}
