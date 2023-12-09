//package com.justedlev.hub.common.activity;
//
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.repository.NoRepositoryBean;
//import org.springframework.data.repository.Repository;
//
//import java.io.Serializable;
//import java.util.List;
//import java.util.Optional;
//
//@NoRepositoryBean
//public interface ActivityRegistrarRepository<T extends Activity<R>, R extends Serializable>
//        extends Repository<T, Long> {
//    Optional<T> findLastById(R id);
//
//    List<T> findAllById(R id);
//
//    Page<T> findAllById(R id, Pageable pageable);
//}
