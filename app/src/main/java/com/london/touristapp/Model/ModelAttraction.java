package com.london.touristapp.Model;

public class ModelAttraction
{


    public String getImageLink() {
        return ImageLink;
    }

    public void setImageLink(String imageLink) {
        ImageLink = imageLink;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public String getTicketLink() {
        return TicketLink;
    }

    public void setTicketLink(String ticketLink) {
        TicketLink = ticketLink;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    private  String ImageLink;
    private  String Name;
    private  String Desc;
    private  String TicketLink;
    private  String ID;


}
