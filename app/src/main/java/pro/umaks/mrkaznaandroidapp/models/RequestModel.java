package pro.umaks.mrkaznaandroidapp.models;

import java.util.Date;

/**
 * Created by kholkin on 18.05.2017.
 */

public class RequestModel 
{
    public String Id;
    public String Number;
    public Date RequestDate;
    public String Organization;
    public String OrganizationAccount;
    public String OrganizationAccountNumber;
    public String OrganizationBank;
    public String Contragent;
    public String Contract;
    public String ContragentAccount;
    public String ContragentBank;
    public String Project;
    public String CashFlowItem;
    public String Description;
    public Date PayDate;
    public String Applicant; //Заявитель
    public double RequestSum;
    public double ApprovedSum;
    public double OutOfBudgetSum;
    public String Stage;
}
