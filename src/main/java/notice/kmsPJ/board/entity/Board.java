package notice.kmsPJ.board.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import notice.kmsPJ.board.audit.Auditable;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity //DB의 테이블을 의미함.
@NoArgsConstructor
@Getter
@Setter
public class Board extends Auditable {
    @Id //pk
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 100, nullable = false) private String title;
    @Column(length = 100, nullable = false) private String content;
    @Column(length = 100, nullable = false) private String username;
    private String filename;
    private String filepath;


}
