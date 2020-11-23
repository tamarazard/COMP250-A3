import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class CatTree implements Iterable<CatInfo>{
    public CatNode root;
    
    public CatTree(CatInfo c) {
        this.root = new CatNode(c);
    }
    
    private CatTree(CatNode c) {
        this.root = c;
    }
    
    
    public void addCat(CatInfo c)
    {
        this.root = root.addCat(new CatNode(c));
    }
    
    public void removeCat(CatInfo c)
    {
        this.root = root.removeCat(c);
    }
    
    public int mostSenior()
    {
        return root.mostSenior();
    }
    
    public int fluffiest() {
        return root.fluffiest();
    }
    
    public CatInfo fluffiestFromMonth(int month) {
        return root.fluffiestFromMonth(month);
    }
    
    public int hiredFromMonths(int monthMin, int monthMax) {
        return root.hiredFromMonths(monthMin, monthMax);
    }
    
    public int[] costPlanning(int nbMonths) {
        return root.costPlanning(nbMonths);
    }
    
    
    
    public Iterator<CatInfo> iterator()
    {
        return new CatTreeIterator();
    }
    
    
    class CatNode {
        
        CatInfo data;
        CatNode senior;
        CatNode same;
        CatNode junior;
        
        public CatNode(CatInfo data) {
            this.data = data;
            this.senior = null;
            this.same = null;
            this.junior = null;
        }
        
        public String toString() {
            String result = this.data.toString() + "\n";
            if (this.senior != null) {
                result += "more senior " + this.data.toString() + " :\n";
                result += this.senior.toString();
            }
            if (this.same != null) {
                result += "same seniority " + this.data.toString() + " :\n";
                result += this.same.toString();
            }
            if (this.junior != null) {
                result += "more junior " + this.data.toString() + " :\n";
                result += this.junior.toString();
            }
            return result;
        }
        
        
        public CatNode addCat(CatNode c) {      	
        	// ADD YOUR CODE HERE
        	if(this.data.monthHired>c.data.monthHired) {
        		if(this.senior==null) {
        			this.senior = c;
               		}
        		else {
        			this.senior = this.senior.addCat(c);
        		}
        		return this;
        	}
        	else if(this.data.monthHired<c.data.monthHired) {
        		if(this.junior==null) {
        			this.junior=c;
        			
        		}
        		else {
        			this.junior = this.junior.addCat(c);
        		}
        		return this;
        	}
        	
        	else { 
        		if(this.data.furThickness >= c.data.furThickness) {
        			if(this.same==null) {
            			this.same = c;
        			}
            		else {
            			this.same = this.same.addCat(c);
            		}
        			return this;
        		}
        		else {
        			c.same = this;
        			return c;
        		}
        	}
        		// DON'T FORGET TO MODIFY THE RETURN IF NEED BE
        }
        
       
        
        public CatNode removeCat(CatInfo c) {
            // ADD YOUR CODE HERE
        	if(c.monthHired==this.data.monthHired) {
        		if(c.furThickness==this.data.furThickness) {
        			if(this.same!=null) {
        				this.data=this.same.data;
        				if(this.same.same!=null) {
        					this.same=this.same.same;
        				}
        				else {
        					this.same=null;
        				}
        				return this;
        			}
        			else if(this.same==null&&this.senior!=null) {
        				CatNode tmp=this.junior;
        				this.same=this.senior.same;
        				this.data=this.senior.data;
        				this.junior=this.senior.junior;
        				this.senior=this.senior.senior;
        				if(tmp!=null) {
        					this.addCat(tmp);
        				}
        				return this;
        			}

        		}
    			else if(this.same==null&&this.senior==null) {
    				if(this.junior!=null) {
    					this.same=this.junior.same;
    					this.data=this.junior.data;
    					this.senior=this.junior.senior;
    					this.junior=this.junior.junior;
    				}
    				else if(this.junior==null) {
    					return null;
    				}
    				return this;
    			}

        	}
    		else if(this.same!=null){
    			this.same.removeCat(c);
        		}
        		else if(c.monthHired<this.data.monthHired) {
        			if(this.senior!=null){
        				this.senior.removeCat(c);
        			}
        			
        		}
        		else {
        			if(this.junior!=null) {
        				this.junior.removeCat(c);
        			}
        		}
    		
            return this; // DON'T FORGET TO MODIFY THE RETURN IF NEED BE
        }
        
        
        public int mostSenior() {
            // ADD YOUR CODE HERE
            if(this.senior == null) {
            	return this.data.monthHired;
            }
            else if (this.senior!=null){
            	return this.senior.mostSenior(); //CHANGE THIS
            }
            else return this.data.monthHired;
        }
        
        public int fluffiest() {
            // ADD YOUR CODE HERE
        	int seniorFluff = 0;
        	int juniorFluff = 0;
        	int maxFluff = 0;
        	        	
        	if(this.senior!=null) {
        		if(this.data.furThickness>this.senior.data.furThickness) {
        			seniorFluff = this.data.furThickness;
        			this.senior.fluffiest();
               		}
        		else {
        			seniorFluff = this.senior.data.furThickness;
        			this.senior.fluffiest();
        		}
        		
        	}else {
        		seniorFluff = this.data.furThickness;
        	}
        	
        	if(this.junior!=null) {
        		if(this.data.furThickness>this.junior.data.furThickness) {
        			juniorFluff = this.data.furThickness;
        			this.junior.fluffiest();
        		}
        		else {
        			juniorFluff = this.junior.data.furThickness;
        			this.junior.fluffiest();
        		}
        	}else {
        		juniorFluff = this.data.furThickness;
        	}
        	
        	maxFluff = Math.max(seniorFluff, juniorFluff);
        	
        	return maxFluff; // DON'T FORGET TO MODIFY THE RETURN IF NEED BE
        }
        
        
        public int hiredFromMonths(int monthMin, int monthMax) {
            // ADD YOUR CODE HERE
        	int ofSame = 0;
        	int ofSenior = 0;
        	int ofJunior = 0;
        	int ofData = 0;
        	int numbOfCats = 0;
        	
        	if(monthMin>monthMax) return 0;
        	
        	if(monthMin<=this.data.monthHired && monthMax>=this.data.monthHired) {
        		ofData =1;
        	}
        	
        	if(this.same!=null) {
        	ofSame = this.same.hiredFromMonths(monthMin, monthMax);
        	}
        	if(this.senior!=null) {
        	ofSenior = this.senior.hiredFromMonths(monthMin, monthMax);
        	}
        	if(this.junior!=null) {
        	ofJunior = this.junior.hiredFromMonths(monthMin, monthMax);
        	}
        	
        	numbOfCats = ofData + ofSame + ofSenior + ofJunior;
        	
        	return numbOfCats;// DON'T FORGET TO MODIFY THE RETURN IF NEED BE
         
        }
        
        public CatInfo fluffiestFromMonth(int month) {
            // ADD YOUR CODE HERE
        	if (this.data.monthHired<month) {
        		if (this.junior==null) {
        			return null;
        		}
        		else return this.junior.fluffiestFromMonth(month);
        	}
        	else if (this.data.monthHired>month) {
        		if (this.senior==null) {
        			return null;
        		}
        		else return this.senior.fluffiestFromMonth(month);
        	}
        	else return this.data;  // DON'T FORGET TO MODIFY THE RETURN IF NEED BE
        }
        
//        private int myMethod(CatNode node, int nb) {
//        	int cost = 0;
//        	
//        	if(node==null) return cost;
//        	
//        	if(node.data.nextGroomingAppointment==nb) {
//        		cost+=node.data.expectedGroomingCost;
//        	}
//        	
//        	myMethod(node.senior, nb);
//        	myMethod(node.junior, nb);
//        	
//        	return cost;
//        }
//        
        
        public int[] costPlanning(int nbMonths) {
            // ADD YOUR CODE HERE
        	int month = 243;
        	CatTree t = new CatTree(this);
   //     	CatNode x = new CatNode(this.data);
        	
         	int[] costPlan = new int[nbMonths];
        	
        	t.iterator();
        	for(int i=0; i<costPlan.length; i++) {
        		for (CatInfo c : t) {
        			if(c.nextGroomingAppointment==month) {
        				costPlan[i] +=c.expectedGroomingCost;
        			}
        		}
        		month++;
        	}
            return costPlan; // DON'T FORGET TO MODIFY THE RETURN IF NEED BE
        }
        
    }
    
    private class CatTreeIterator implements Iterator<CatInfo> {
        // HERE YOU CAN ADD THE FIELDS YOU NEED
    	//private CatNode next;
    	
    	ArrayList <CatInfo> list = new ArrayList<CatInfo>();
    	private CatInfo info;
    	int index;
    	
    	private ArrayList <CatInfo> inOrder(CatNode node, ArrayList <CatInfo> list){
    		if(node!=null) {
    			inOrder(node.senior, list);
    			inOrder(node.same, list);
    			list.add(node.data);
    			inOrder(node.junior, list);
    		}
    	return list;
    	}
   
    	
        public CatTreeIterator() {
            //YOUR CODE GOES HERE
           	this.list = inOrder(root, list);
           	this.index = 0;
           	this.info = list.get(0);
        }
        
        public CatInfo next(){
            //YOUR CODE GOES HERE
        	CatInfo tmp = this.info;
        	
        	if(index==list.size()-1) {
        		this.info=null;
        	}
        	else this.info = this.list.get(this.index+1);
        	
        	index++;
            return tmp; // DON'T FORGET TO MODIFY THE RETURN IF NEED BE
        }
        
        public boolean hasNext() {
            //YOUR CODE GOES HERE
            return (info!=null); // DON'T FORGET TO MODIFY THE RETURN IF NEED BE
        }
 
    }
    
}

