package pt.isec.pa.javalife.model.data.elements;

public sealed interface IElementoComForca
        permits Fauna, Flora {
    double getForca();
    void setForca(double forca);
}
