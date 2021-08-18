package com.teamgreen.bacefook.entity;

import javax.persistence.*;

import java.util.Date;

@Entity

@Table(name = "post")

public class Post extends Message
{
    @Id

    @GeneratedValue(strategy = GenerationType.AUTO)

    private long id;

    @ManyToOne(fetch = FetchType.EAGER)

    private User userPosted;

    private Date timePosted;

    public Post()
    {


    }

    public long getId()
    {
        return id;
    }

    public void setId(long id)
    {
        this.id = id;
    }

    public User getUserPosted()
    {
        return userPosted;
    }

    public void setUserPosted(User userPosted)
    {
        this.userPosted = userPosted;
    }

    public Date getTimePosted()
    {
        return timePosted;
    }

    public void setTimePosted(Date TimePosted)
    {
        this.timePosted = timePosted;
    }


}
