package jgibblda;

public class InferenceNewDoc {
	public static void main(String[] args){
		LDACmdOption ldaOption = new LDACmdOption(); 
		ldaOption.inf = true; 
		ldaOption.dir = "D:/"; 
		ldaOption.modelName = "model-final"; 
		ldaOption.niters = 200;

		Inferencer inferencer = new Inferencer(); 
		inferencer.init(ldaOption);
		
		String [] test = {"use https when queri weather info ", "initi commit ", "modifi readm "};
		Model newModel = inferencer.inference(test);
		newModel.saveModel("test");
	}
}
