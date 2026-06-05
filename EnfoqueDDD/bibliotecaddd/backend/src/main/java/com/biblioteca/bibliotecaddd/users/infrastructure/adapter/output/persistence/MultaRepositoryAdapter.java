package com.biblioteca.bibliotecaddd.users.infrastructure.adapter.output.persistence;

import com.biblioteca.bibliotecaddd.users.domain.model.Multa;
import com.biblioteca.bibliotecaddd.users.domain.model.valueobjects.UserId;
import com.biblioteca.bibliotecaddd.users.domain.repository.MultaRepository;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class MultaRepositoryAdapter implements MultaRepository {

    private final MultaJpaRepository jpaRepository;

    public MultaRepositoryAdapter(MultaJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(Multa multa) {
        MultaJpaEntity entity = new MultaJpaEntity(
                multa.getUsuarioId().getValue(),
                multa.getMontoPendiente()
        );
        jpaRepository.save(entity);
    }

    @Override
    public Optional<Multa> findByUsuarioId(UserId id) {
        return jpaRepository.findById(id.getValue())
                .map(entity -> new Multa(new UserId(entity.getUsuarioId()), entity.getMontoPendiente()));
    }

    @Override
    public boolean existsByUsuarioId(UserId id) {
        return jpaRepository.existsById(id.getValue());
    }
}