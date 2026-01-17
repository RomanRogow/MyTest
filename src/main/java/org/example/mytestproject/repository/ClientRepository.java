package org.example.mytestproject.repository;

import org.example.mytestproject.entity.Client;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("select c from Client c JOIN FETCH c.payments")
    List<Client> findAllClientsFoJoinFeatch();

    @Query("select c from Client c")
    @EntityGraph(attributePaths = {"payments"})
    List<Client> findAllClientsFoEntityGraph();
}
