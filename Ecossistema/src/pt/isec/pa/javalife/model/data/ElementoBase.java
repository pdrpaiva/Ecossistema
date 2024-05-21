package pt.isec.pa.javalife.model.data;

public sealed class ElementoBase implements IElemento permits Inanimado, Flora, Fauna {
    // Implementação comum
}

public final class Inanimado extends ElementoBase {
    // Implementação específica
}

public final class Flora extends ElementoBase implements IElementoComForca, IElementoComImagem {
    // Implementação específica
}

public final class Fauna extends ElementoBase implements IElementoComForca, IElementoComImagem {
    // Implementação específica
}

