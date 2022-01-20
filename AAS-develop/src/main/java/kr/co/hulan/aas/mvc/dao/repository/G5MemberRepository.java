package kr.co.hulan.aas.mvc.dao.repository;

import kr.co.hulan.aas.mvc.model.domain.G5Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface G5MemberRepository extends JpaRepository<G5Member, Long> {

    Optional<G5Member> findByMbId(String mbId);

    Optional<G5Member> findByMbIdAndMbLevel(String mbId, Integer mbLevel);

    int deleteByMbId(String mbId);

    boolean existsByMbId(String mbId);

    @Query(value=" SELECT count(G5M.mb_id) "
        +" FROM g5_member G5M "
        +" WHERE  ( "
        +"   replace(G5M.mb_id, '-', '' ) = replace( :mbKey, '-', '' ) "
        +"   OR "
        +"   replace(G5M.mb_hp, '-', '' ) = replace( :mbKey, '-', '' ) "
        +"   OR "
        +"   replace(G5M.mb_tel, '-', '' ) = replace( :mbKey, '-', '' ) "
        +" ) ",
        nativeQuery = true
    )
    Long countDuplicatedMemberCount(@Param("mbKey") String mbKey );


    @Query(value=" SELECT count(G5M.mb_id) "
        +" FROM g5_member G5M "
        +" WHERE  ( "
        +"   replace(G5M.mb_id, '-', '' ) = replace( :mbKey, '-', '' ) "
        +"   OR "
        +"   replace(G5M.mb_hp, '-', '' ) = replace( :mbKey, '-', '' ) "
        +"   OR "
        +"   replace(G5M.mb_tel, '-', '' ) = replace( :mbKey, '-', '' ) "
        +" ) "
        +" AND G5M.mb_id != :mbId ",
        nativeQuery = true
    )
    Long countDuplicatedMemberAndNotMbIdCount(@Param("mbKey") String mbKey, @Param("mbId") String mbId );

}
