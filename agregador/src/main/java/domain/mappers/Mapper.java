package domain.mappers;

public interface Mapper <Input, Output> {
    Output map(Input input);
}
