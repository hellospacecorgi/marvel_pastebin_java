package marvel.model;

public interface ModelFacade {
    public InputModel getInputSubModel();
    public OutputModel getOutputSubModel();

    public CharacterInfo getCharacterInfo(String name);

    //public boolean sendReport();
}
