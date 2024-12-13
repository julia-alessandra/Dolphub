package com.cefet.dolphub.Service;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.cefet.dolphub.Entidades.Main.Tag;
import com.cefet.dolphub.Entidades.Recursos.Alternativa;
import com.cefet.dolphub.Entidades.Recursos.Questao;
import com.cefet.dolphub.Entidades.Recursos.QuestaoRespondida;

import com.cefet.dolphub.Entidades.Recursos.Topico;
import com.cefet.dolphub.Repositorio.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class QuestaoService {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private QuestaoRespondidaRepository questaoRespondidaRepository;
    @Autowired
    private QuestaoRepository questaoRepository;
    @Autowired
    private AlternativaRepository alternativaRepository;
    @Autowired
    private TagRepository tagRepository;

    public Questao buscar(Long id) {
        Optional<Questao> questao = questaoRepository.findById(id);
        return questao.orElseThrow(() -> new RuntimeException("Questão não encontrado!"));
    }

    public Topico buscarTopicoPai(Long id) {
        Optional<Topico> topico = topicoRepository.findById(id);
        return topico.orElseThrow(() -> new RuntimeException("Tópico não encontrado!"));
    }

    // public Questao atualizarQuestao(Long id, Questao questaoAtualizada,
    // List<String> descricoes,
    // List<Boolean> verificacoes, List<String> novosNomesTags) {
    // Questao questaoExistente = buscar(id);

    // if (questaoExistente == null)
    // return null;

    // questaoExistente.setEnunciado(questaoAtualizada.getEnunciado());
    // questaoExistente.setDificuldade(questaoAtualizada.getDificuldade().getValor());

    // questaoExistente.getAlternativas().clear();

    // System.out.println("Alt:");
    // listarItens(descricoes);
    // listarItens(verificacoes);
    // System.out.println(descricoes.size());
    // System.out.println(verificacoes.size());

    // for (int i = 0; i < descricoes.size(); i++) {
    // Alternativa alternativa = new Alternativa();
    // alternativa.setDescricao(descricoes.get(i));
    // alternativa.setVerificacao(verificacoes.get(i + 1));
    // alternativa.setQuestao(questaoExistente);
    // questaoExistente.getAlternativas().add(alternativa);
    // }

    // List<Tag> novasTags = new ArrayList<>();
    // for (String nomeTag : novosNomesTags) {
    // Tag tag = tagRepository.findByNomeIgnoreCase(nomeTag)
    // .orElseGet(() -> {
    // Tag novaTag = new Tag();
    // novaTag.setNome(nomeTag);
    // tagRepository.save(novaTag);
    // return novaTag;
    // });
    // novasTags.add(tag);
    // }

    // questaoExistente.setTags(novasTags);

    // questaoRepository.save(questaoExistente);
    // return questaoExistente;
    // }

    public Alternativa buscarAlternativa(Long id) {
        Optional<Alternativa> alternativa = alternativaRepository.findById(id);
        return alternativa.orElseThrow(() -> new RuntimeException("Questão não encontrado!"));
    }

    public Questao salvarQuestao(Questao questao) {
        return questaoRepository.save(questao);
    }

    public Alternativa alternativaCorreta(Long id) {
        Questao questao = this.buscar(id);
        for (Alternativa alternativa : questao.getAlternativas()) {
            if (alternativa.getVerificacao())
                return alternativa;
        }
        throw new RuntimeException("Nenhuma alternativa correta encontrada para a questão com ID: " + id);
    }

    public Boolean verificarAlternativa(Long idQuestao, Long idAlternativa) {
        Alternativa alternativaCorreta = this.alternativaCorreta(idQuestao);

        System.out.println("Teste printar alt");
        System.out.println(alternativaCorreta.getId());
        System.out.println(idAlternativa);
        if (alternativaCorreta.getId() == idAlternativa)
            return true;
        return false;
    }

    public int quantidadeDeAcertos(Long idUsuario) {
        int acertos = 0;
        List<QuestaoRespondida> questoesRespondidas = this.listaQuestoesRepondidas(idUsuario);
        for (QuestaoRespondida questao : questoesRespondidas) {
            Long idQuestao = questao.getQuestao().getId();
            Long idAlternativa = questao.getAlternativa().getId();
            if (this.verificarAlternativa(idQuestao, idAlternativa)) {
                ++acertos;
                System.out.println("O id da questão é " + idQuestao);
            }
        }
        return acertos;
    }

    public void deletar(Long id) {
        questaoRepository.deleteById(id);
    }

    public List<Questao> listarTodas() {
        return questaoRepository.findByStatus("ativo");
    }

    public List<Questao> listarTodasPorCurso(Long id) {
        return questaoRepository.findByCursoIdAndStatus(id, "ativo");
    }

    public List<QuestaoRespondida> listaQuestoesRepondidas(Long id) {
        return questaoRespondidaRepository.findByUsuarioId(id);
    }

    private <T> void listarItens(List<T> lista) {
        if (lista == null || lista.isEmpty()) {
            System.out.println("A lista está vazia.");
            return;
        }

        for (T item : lista) {
            System.out.println(item);
        }
    }

    public void desativarQuestao(Long questaoId) {
        Questao questao = questaoRepository.findById(questaoId)
                .orElseThrow(() -> new IllegalArgumentException("Questão não encontrada com o ID: " + questaoId));

        questao.setStatus("inativo");

        questaoRepository.save(questao);
    }

    public List<Questao> buscarQuestoesFiltradas(Long cursoId, Date inicio, Date fim, String palavraChave) {
        List<Questao> questoesAtivas = questaoRepository.findByCursoIdAndStatus(cursoId, "ativo");

        System.out.println("Datas:");
        System.out.println(inicio);
        System.out.println(fim);

        return questoesAtivas.stream()
                .filter(questao -> (inicio == null || !questao.getDataCriacao().before(inicio)))
                .filter(questao -> (fim == null || !questao.getDataCriacao().after(fim)))
                .filter(questao -> (palavraChave == null
                        || questao.getEnunciado().toLowerCase().contains(palavraChave.toLowerCase())))
                .collect(Collectors.toList());
    }

}
