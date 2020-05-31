package project.company;

import project.ticket.dao.TicketDao;

import java.util.List;

public class CompanyService {

    public List<String> getCompaniesForComboBox(){
        return CompanyRepository.getInstance().getCompaniesForComboBox();
    }

    public Company getCompany(int id){
        return CompanyRepository.getInstance().getCompany(id);
    }

    public List<TicketDao> getCompaniesTickets(int id){
        return CompanyRepository.getInstance().getCompaniesTickets(id);
    }

    public String deleteCompany(int id){
        return CompanyRepository.getInstance().deleteCompany(id);
    }

    public String newCompany(Company c) {
        return CompanyRepository.getInstance().newCompany(c);
    }

    public String updateCompany(Company c){
        return CompanyRepository.getInstance().updateCompany(c);
    }

}
