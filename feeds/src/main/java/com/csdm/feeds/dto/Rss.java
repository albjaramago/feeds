package com.csdm.feeds.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="rss")
public class Rss
{
    private Channel channel;

    private String version;

    public Channel getChannel ()
    {
        return channel;
    }

    public void setChannel (Channel channel)
    {
        this.channel = channel;
    }

    public String getVersion ()
    {
        return version;
    }

    public void setVersion (String version)
    {
        this.version = version;
    }


}