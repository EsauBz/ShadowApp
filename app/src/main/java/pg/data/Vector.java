package pg.data;

/**
 * A class to handle homogenous vectors in projective geometry
 * @author Cedric Telegone, ECN 2010
 */
public class Vector{

	protected double x;
	protected double y;
	protected double z;

	/**
	 * Constructor without parameter (Create a null vector)
	 */
	public Vector(){
		x=0;
		y=0;
		z=0;
	}

	/**
	 * Copy constructor (superficial copy in the space)
	 * @param x
	 * @param y
	 * @param z
	 */
	public Vector(double x,double y,double z){
		this.x=x;
		this.y=y;
		this.z=z;
	}

	/**
	 * Copy constructor (superficial copy in the plan)
	 * @param x
	 * @param y
	 */
	public Vector(double x, double y){

		this.x=x;
		this.y=y;
		z=1;
	}

	/**
	 * X's Getter
	 * @return X coordinate
	 */
	public double getX() {
		return x;
	}

	/**
	 * to normalize the vector (z=1)
	 * @return the vector normalized
	 */
	public Vector normalize(){
		if(z==0)
			return this;
		else
			return new Vector(x/z,y/z,1);
	}

	/**
	 * X's Setter
	 * @param x coordinate
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * Y's Getter
	 * @return y coordinate
	 */
	public double getY() {
		return y;
	}

	/**
	 * Y's Setter
	 * @param y coordinate
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * Z's Setter
	 * @param z coordinate
	 */
	public void setZ(double z) {
		this.z = z;
	}

	/**
	 * Z's Getter
	 * @return z coordinate
	 */
	public double getZ() {
		return z;
	}


	/**
	 * test the equality of homogeneous vectors
	 * @param p, an homogeneous vector
	 * @return true if p equals to the this.vector
	 */
	public boolean equals(Vector p){
		this.normalize();
		p.normalize();
		if(this.x==p.getX()&&this.y==p.getY()&&this.z==p.getZ())
			return true;
		else
			return false;
	}

	/**
	 * scalar product
	 * @param v, a vector
	 * @return a scalar
	 */
	public double scalar(Vector v){
		double result;
		result=x*v.getX()+y*v.getY()+z*v.getZ();
		return result;
	}

	/**
	 * distance fonction
	 * @param v, a vector
	 * @return a distance
	 */
	public double distance(Vector v){
		if(z==0||v.getZ()==0)
			return 0;
		else{
			double dX=x/z-v.getX()/v.getZ();
			double dY=y/z-v.getY()/v.getZ();
			return Math.sqrt(dX*dX+dY*dY);
		}
	}

	/**
	 * cross product (product of vectors)
	 * @param v, a vector
	 * @return a cross product
	 */
	public Vector cross(Vector v){
		Vector result=new Vector();
		result.setX(y*v.getZ()-v.getY()*z);
		result.setY(-x*v.getZ()+v.getX()*z);
		result.setZ(x*v.getY()-v.getX()*y);
		return result;
	}

	/**
	 * display vector coordinates
	 */
	public void print(){
		System.out.println("["+x+" "+y+" "+z+"]");
	}

}
