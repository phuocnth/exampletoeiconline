package vn.myclass.core.persistence.entity;


import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "comment")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentId;

    @Column(name = "content")
    private String contente;

    @Column(name = "createddate")
    private Timestamp createdDate;

    @ManyToOne
    @JoinColumn(name = "userid")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "listenguidelinecommentid")
    private ListenGuidelineEntity listenGuidelineEntity;

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public String getContente() {
        return contente;
    }

    public void setContente(String contente) {
        this.contente = contente;
    }

    public Timestamp getCreateDate() {
        return createdDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createdDate = createDate;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public ListenGuidelineEntity getListenGuidelineEntity() {
        return listenGuidelineEntity;
    }

    public void setListenGuidelineEntity(ListenGuidelineEntity listenGuidelineEntity) {
        this.listenGuidelineEntity = listenGuidelineEntity;
    }
}
