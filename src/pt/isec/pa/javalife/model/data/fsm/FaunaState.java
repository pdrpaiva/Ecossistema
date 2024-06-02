    package pt.isec.pa.javalife.model.data.fsm;

    import pt.isec.pa.javalife.model.data.elements.Fauna;
    import pt.isec.pa.javalife.model.data.fsm.states.*;

    public enum FaunaState {
        MOVIMENTO, PROCURA_COMIDA, ALIMENTACAO,ATAQUE;


        public IFaunaState getInstance(FaunaContext context, Fauna data){
            return switch (this){
                case MOVIMENTO -> new MovimentoState(context,data);
                case PROCURA_COMIDA -> new ProcuraComidaState(context,data);
                case ALIMENTACAO -> new AlimentacaoState(context,data);
                case ATAQUE -> new AtaqueState(context,data);
            };
        }
    }
