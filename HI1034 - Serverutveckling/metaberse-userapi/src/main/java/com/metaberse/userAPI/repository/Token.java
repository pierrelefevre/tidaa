package com.metaberse.userAPI.repository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "token")
public class Token {
    @Id
    @Column(name = "tokenid", nullable = false)
    private UUID tokenid;

    @Column(name = "timestamp", nullable = false)
    private Timestamp timestamp;

    @Column(name = "userid", nullable = false)
    private Long userid;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return Objects.equals(tokenid, token.tokenid) && Objects.equals(timestamp, token.timestamp) && Objects.equals(userid, token.userid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tokenid, timestamp, userid);
    }

    public Token(UUID tokenid, Timestamp timestamp, Long userid) {
        this.tokenid = tokenid;
        this.timestamp = timestamp;
        this.userid = userid;
    }

    public Token() {
    }


    public UUID getTokenid() {
        return tokenid;
    }

    public void setTokenid(UUID tokenid) {
        this.tokenid = tokenid;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }
}
