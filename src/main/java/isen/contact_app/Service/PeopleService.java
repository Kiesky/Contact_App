package isen.contact_app.Service;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import isen.contact_app.Dao.PeopleDao;
import isen.contact_app.Entities.People;


import java.io.*;
import java.sql.Date;



public class PeopleService {


    static PeopleDao peopleDao = new PeopleDao();
    private ObservableList<People> peopleObservableList;

    private PeopleService(){
        peopleObservableList= FXCollections.observableArrayList();
        peopleObservableList.addAll(peopleDao.listOfPeople());
        if(peopleObservableList.size()<=0) {
            peopleDao.addPeople(new People(0, "Boateng", "Samuel", "Sammybee", "0751544735", "1 rue d`austerlitz tourcoing", "sbee1796@gmail.com", 
            new Date(97,10,20)));
            peopleDao.addPeople(new People(0, "Tobbal", "Kevin", "Kevin", "072694590", "52 rue des fleurs", "kevintobbal@gmail.com",
                   new Date(98,3,20)));
            peopleDao.addPeople(new People(0, "Hope", "James", "Handsome", "076369211", "25 rue de eglise ", "hopejames@gmail.com",
                    new Date(98,1,28)));
            peopleDao.addPeople(new People(1, "Mary", "Johnson", "show girl", "076154211", "56 rue emile    zola", "maryjohnson@gmail.com",
                    new Date(97,12,20)));
            peopleDao.addPeople(new People(0, "Steveson", "Ken", "cloud", "072694555", "8 rue des fleurs", "cloudsteveson@gmail.com",
                    new Date(99,11,20)));

            peopleObservableList.addAll(peopleDao.listOfPeople());
        }




    }
    public static ObservableList<People> getPeople() {
        return PeopleServiceHolder.INSTANCE.peopleObservableList;
    }

    public static void addPeople(People people) {
        peopleDao.addPeople(people);
        PeopleServiceHolder.INSTANCE.peopleObservableList.add(people);
    }
    public static void updatePeople(People people){

//        addressCol.setOnEditCommit(t -> {
//            t.getTableView().getItems().get(
//                    t.getTablePosition().getRow()).setAddress(t.getNewValue());
//            peopleDao.updatePeople("address",t.getNewValue(), t.getTableView().getItems().get(
//                    t.getTablePosition().getRow()).getId());
//        });
    }
    public static void deletePeople(People people){
        peopleDao.deletePeople(people.getId());
        PeopleServiceHolder.INSTANCE.peopleObservableList.remove(people);
    }
    public static void exportToFile(People p ) {

        try {
            File f = new File("./src/main/resources/files/peoples.vcf");
            FileOutputStream fop = new FileOutputStream(f,true);
            if (f.exists()) {
                String str = "BEGIN:VCARD\n" + "VERSION:4.0\n"+ "N:People;;;\n" + "ID:" + p.getId() + "\n"
                        + "FIRSTNAME:" + p.getFirstName() + "\n" + "LASTNAME:" + p.getLastName()
                        + "\n" + "NICKNAME:" + p.getNickName() + "\n" + "PHONENUMBER:"
                        + p.getPhoneNumber() + "\n" + "EMAIL:" + p.getEmailAddress() + "\n"
                        + "ADDRESS:" + p.getAddress() + "BIRTHDAY:"
                        + p.getBirthDate()+ "\n"

                        + "END:VCARD\n";

                fop.write(str.getBytes());

                fop.flush();
                fop.close();
                System.out.println("The data has been written");
            } else
                System.out.println("This file does not exist");
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }
    public static void readFile(){
        try{
            BufferedReader br;
            String sCurrentLine;
            br = new BufferedReader(new FileReader("./src/main/resources/files/peoples.vcf"));
            while ((sCurrentLine = br.readLine()) != null) {
                System.out.println(sCurrentLine);
                // System.out.println(f.getAbsolutePath());
            }
            br.close();

        }
        catch (IOException e){
            e.printStackTrace();

        }
    }



    private static class PeopleServiceHolder {
        private static final PeopleService INSTANCE = new PeopleService();
    }
}
