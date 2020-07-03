package com.tutorials.kiwilearn;

public class Courses {
    private String UserAccount;
    private String Title;
    private String Category;
    private String Introduction;
    private String Requirements;
    private String Fees;
    private String Contact;
    private String StartDate;
    private String EndDate;
    private String Status;

    public Courses(String userAccount, String title, String category,
                   String introduction, String requirements, String fees,
                   String contact, String startDate, String endDate,String status) {
        this.UserAccount = userAccount;
        this.Title = title;
        this.Category = category;
        this.Introduction = introduction;
        this.Requirements = requirements;
        this.Fees = fees;
        this.Contact = contact;
        this.StartDate = startDate;
        this.EndDate = endDate;
        this.Status = status;
    }

    public String GetAccount() {
        return this.UserAccount;
    }
    public String GetTitle(){
        return this.Title;
    }
    public String GetCategory(){
        return this.Category;
    }
    public String GetIntroduction(){
        return this.Introduction;
    }
    public String GetRequirement(){
        return this.Requirements;
    }
    public String GetFees(){
        return this.Fees;
    }
    public String GetContact(){
        return this.Contact;
    }
    public String GetStartDate(){
        return this.StartDate;
    }
    public String GetEndDate(){
        return this.EndDate;
    }
    public String GetStatus(){return this.Status;}
}
