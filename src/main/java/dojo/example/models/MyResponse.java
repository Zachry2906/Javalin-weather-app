package dojo.example.models;

public class MyResponse<T> {
    public MetaData metaData;
    public T data;

    public MyResponse(MetaData metaData, T data) {
        this.metaData = metaData;
        this.data = data;
    }
}
