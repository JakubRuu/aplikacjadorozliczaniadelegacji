package com.appRachunek.AplikacjaDoRozliczaniaDelegacji.stanowisko;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

interface AddFunction {
}

interface UpdateFunction {
}

@Entity
public class Function {

    @Id
    @GeneratedValue
    private Long id;

    @Size(min = 2, max = 20, groups = {AddFunction.class, UpdateFunction.class})
    @NotBlank(groups = AddFunction.class)
    private String name;
    private String function;

    public Function(String name, String function) {
        this.name = name;
        this.function = function;
    }

    public Function(Long id, String name, String function) {
        this.id = id;
        this.name = name;
        this.function = function;
    }

    public Function() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    @Override
    public String toString() {
        return "Function{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", function='" + function + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Function function1 = (Function) o;
        return Objects.equals(id, function1.id) && Objects.equals(name, function1.name) && Objects.equals(function, function1.function);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, function);
    }
}
