package notice.kmsPJ.board.repository;


import notice.kmsPJ.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Repository;

@EnableJpaAuditing
@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> { // Integer는 PK의 타입을 설정해야함.
}
