package org.example.springhemingproject02.repository;

import org.example.springhemingproject02.dox.NodeClosure;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NodeClosureRepository extends ListCrudRepository<NodeClosure, String> {
}
