
import lombok.Data;
import lombok.Getter;

@Data
class User {
    private String lastName;
    private String firstName;

    public User(String last, String first){
        this.firstName = first;
        this.lastName = last;
    }

    public String getFirstName(){ return this.firstName;}
    public String getLastName(){ return this.lastName;}
}