package org.example.repository;

import org.example.entity.UserNote;
import org.springframework.data.couchbase.repository.CouchbaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserNoteRepository extends CouchbaseRepository<UserNote, String> {
    List<UserNote> findByUserId(String userId);
}
