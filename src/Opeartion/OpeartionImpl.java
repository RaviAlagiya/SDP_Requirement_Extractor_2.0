package Opeartion;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import Commands.AddComponent;
import Commands.EditComponent;
import Commands.GetChildList;
import Commands.GetComponent;
import Commands.ListCommand;
import Commands.RemoveComponent;
import composite.CompositeComponent;
import composite.RequirementComponent;
import exportDocument.ExportRequirement;

public class OpeartionImpl implements OpeartionInterface{

	private static CompositeComponent root;
	
	private Stack<ListCommand> executedStack = new Stack<ListCommand>();
    private Stack<ListCommand> undoneStack = new Stack<ListCommand>();
	
    public OpeartionImpl(){
    	root = new CompositeComponent(null);
    }
    
    @Override
    public boolean undo()
    {
        if (executedStack.size() > 0)
        {
        	ListCommand cmd = executedStack.pop();
            Object result = cmd.undoExecute();
            undoneStack.push(cmd); 
            
            return (boolean)result;
        }
        
        return false;
    }

    @Override
    public boolean redo()
    {
        if (undoneStack.size() > 0)
        {
        	ListCommand cmd = undoneStack.pop();
            Object result = cmd.execute();
            executedStack.push(cmd);
            return (boolean)result;
        }
        
        return false;
    }

	@Override
	public boolean addComponent(RequirementComponent parent,
			RequirementComponent child, int index) {
		
		ListCommand add = new AddComponent( parent,child, index);
		
		boolean result = (boolean)add.execute();
		
		if(result){
			executedStack.push(add);
		}
		
		return result;
	}

	@Override
	public boolean removeComponent(RequirementComponent child) {
		
		ListCommand remove = new RemoveComponent(child);
		
		boolean result = (boolean)remove.execute();
		
		if(result){
			executedStack.push(remove);
		}
		
		return result;
	}

	@Override
	public List<RequirementComponent> getChildList(RequirementComponent parent) {
		
		ListCommand getChild = new GetChildList(parent);
		
		return (List<RequirementComponent>)getChild.execute();
	}

	@Override
	public RequirementComponent getComponent(String id) {
		
		ListCommand getComponent =  new GetComponent(root, id);
		
		return (RequirementComponent) getComponent.execute();
	}

	@Override
	public boolean editComponent(RequirementComponent oldCom,
			RequirementComponent newCom, int newIndex) {

		ListCommand edit = new EditComponent( oldCom,newCom,  newIndex);
		
		boolean result = (boolean)edit.execute();
		
		if(result){
			executedStack.push(edit);
		}
		
		return result;
	}

	@Override
	public String generateRequirement() {
		String requirement = "";
		String tab = "      ";
        
        List<RequirementComponent> bpList = ((CompositeComponent)root).getChild();
        
        for(int i = 0; i< bpList.size(); i++){
            String reqID = "R"+(i+1)+".";
        	requirement = requirement 
        			+reqID+ bpList.get(i).getPhrase().getSentence() +"\n";
        	
        	List<RequirementComponent> stepList = ((CompositeComponent)bpList.get(i)).getChild();
        	
        	for(int j = 0; j<stepList.size(); j++){
        		String stepID = reqID + (j+1) +".";
        		requirement = requirement +
        				tab+stepID+ stepList.get(j).getPhrase().getSentence() +"\n";
        		
            	List<RequirementComponent> actionList = ((CompositeComponent)stepList.get(j)).getChild();
            	
            	for(int k =0; k<actionList.size(); k++){
            		String actionID = stepID + (k+1) +".";
            		requirement = requirement + tab+
            				tab+actionID+ actionList.get(k).getPhrase().getSentence() +"\n";
            	}
        	}
        }
        
		return requirement;
	}

	@Override
	public boolean exportRequirement(String requirement, String path, String extension) {
		ExportRequirement exp = new ExportRequirement();
		exp.ExportData(requirement, path, extension);
		return false;
	}
    
}
