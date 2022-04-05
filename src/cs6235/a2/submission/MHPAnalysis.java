package cs6235.a2.submission;

import java.util.Map;
import java.util.Set;

import cs6235.a2.AnalysisBase;
import soot.Local;
import soot.PointsToAnalysis;
import soot.PointsToSet;
import soot.Scene;
import soot.SootMethod;
import soot.Type;
import soot.jimple.spark.sets.DoublePointsToSet;

public class MHPAnalysis extends AnalysisBase {

	@Override
	public String getResultString() {
		// TODO Auto-generated method stub
		return "I am the reference MHP-A";
	}

	@Override
	protected void internalTransform(String phaseName, Map<String, String> options) {
		
		//say we want to know the runtime types of each local in Main.main
		SootMethod mainMethod = Scene.v().getMainMethod();
		
		PointsToAnalysis pta = Scene.v().getPointsToAnalysis();
		
		for(Local local : mainMethod.getActiveBody().getLocals()) {
			PointsToSet pts = pta.reachingObjects(local);
			//cg.spark returns an instance of DoublePointsToSet
			DoublePointsToSet doublePTS = (DoublePointsToSet) pta.reachingObjects(local);
			
			Set<Type> types = pts.possibleTypes();
			System.out.println(local + ": types are " + types);
			System.out.println();
		}
		
		//now lets try to determine if two locals, say t1 and t2 are aliases

		Local t1 = null;
		Local t2 = null;
		for(Local local : mainMethod.getActiveBody().getLocals()) {
			if(local.getName().equals("t1"))
				t1 = local;
			else if(local.getName().equals("t2"))
				t2 = local;
			else 
				continue;
		}
		

		PointsToSet pts_t1 = pta.reachingObjects(t1);
		PointsToSet pts_t2 = pta.reachingObjects(t2);
		
		boolean isAlias = pts_t1.hasNonEmptyIntersection(pts_t2);
		System.out.println(isAlias);

	}

}
