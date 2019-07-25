//Name: Tao.ge;
//ID: 201218973;
public class Engineers {

	public static void main(String[] args) {
		// create outer object
		Engineers e = new Engineers();
		Copyist[] copyists = new Copyist[8];// create the set of copyist
		Pencil[] pencils = new Pencil[4];// create the set of pencil
		Eidographs[] eidographs = new Eidographs[4];// create the set of
													// eidograph
		for (int i = 0; i < 4; i++) {
			eidographs[i] = e.new Eidographs(i * 2);// instantiate all 4
													// eidographs
			pencils[i] = e.new Pencil(i * 2 + 1);// instantiate all 4 pencils
		}
		// instantiate all 8 copyists with tool index number,
		// each copyist owns the object pointer referring to the exact pencil
		// and eidograph object around his hands.

		for (int i = 0; i < 8; i++) {
			if (i % 2 != 0) {
				copyists[i] = e.new Copyist(i, pencils[(i - 1) / 2], eidographs[(i - 1) / 2]);
				// instantiate odd copyist
			} else {
				copyists[i] = e.new Copyist(i, pencils[i / 2], eidographs[i / 2]);
				// instantiate even copyist
			}
		}
		for (Copyist copyist : copyists) {
			copyist.start();
		}
	}

	class Copyist extends Thread {
		private int index;// create the index to identify different people
		private int complete = 0;// define the production£¬each copyist must
									// finish 5 drawing and initial number is 0
		private Pencil pencil;
		private Eidographs eidographs;
		static final int copyTimeOdd_Min = 30;// define the constants of the
												// working time
		static final int copyTimeOdd_Max = 80;
		static final int copyTimeEven_Min = 30;
		static final int copyTimeEven_Max = 60;
		static final int checkTimeOdd_Min = 40;
		static final int checkTimeOdd_Max = 60;
		static final int checkTimeEven_Min = 40;
		static final int checkTimeEven_Max = 100;

		// constructor, once the object constructed the tool is identified.
		public Copyist(int id, Pencil pencil, Eidographs eidographs) {
			this.index = id;
			this.eidographs = eidographs;
			this.pencil = pencil;
		}

		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}

		// verify if the copyist index is odd or even
		public boolean isOdd() {
			return index % 2 != 0;
		}

		// generate the random time, divided into two situation
		public int copyTime() {
			if (isOdd()) {
				return (int) (Math.random() * (copyTimeOdd_Max - copyTimeOdd_Min) + copyTimeOdd_Min);
			} else {
				return (int) (Math.random() * (copyTimeEven_Max - copyTimeEven_Min) + copyTimeEven_Min);
			}
		}

		// generate the random time, divided into two situation
		public int checkTime() {
			if (isOdd()) {
				return (int) (Math.random() * (checkTimeOdd_Max - checkTimeOdd_Min) + checkTimeOdd_Min);
			} else {
				return (int) (Math.random() * (checkTimeEven_Max - checkTimeEven_Min) + checkTimeEven_Min);
			}
		}

		// extract the print code in the output code for sake of convenience
		public String printIndex() {
			return "Copyist " + this.getIndex() + " ";
		}

		@Override
		public void run() {
			while (complete < 5) {
				pencil.get();// once started each thread is asked to get the
								// pencil first, so the situation that everyone
								// get
								// the tool from the same handset simultaneously
								// is avoided, so the deadlock could be avoided
				System.out.println(printIndex() + "is using pencil.");
				eidographs.get();
				System.out.println(printIndex() + "is using eidigraph");
				int s = copyTime();// create one variable to store the random
									// time generated
				try {
					System.out.println(printIndex() + "is making drawing \t\t" + (complete + 1));
					sleep(s*1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
				pencil.putDown();
				System.out.println(printIndex() + "has finished with pencil.");
				eidographs.putDown();
				System.out.println(printIndex() + "has finished with eidograph.");
				System.out.println(printIndex() + "has finished drawing \t\t" + (complete + 1));
				System.out.println("Drawing time: [" + s + "]");
				s = checkTime();
				try {
					System.out.println(printIndex() + "is checking diagram \t\t" + (complete + 1));
					sleep(s*1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println(printIndex() + "has finished checking diagram " + (complete + 1));
				System.out.println("Checking time: [" + s + "]");
				complete++;// if program run to this code, it indicates the
							// copyist has completed one copy, so add one
							// product
			}
			System.out.println(printIndex() + "has finished work and gone to pub.");
		}
	}

	public class Pencil {
		private int index;
		private boolean occupied = false;// flag to identify the status of this
											// tool

		public Pencil(int index) {
			this.index = index;
		}

		public synchronized void get() {
			Copyist copyist = (Copyist) Thread.currentThread();
			// get the copyist object that currently calling this method
			while (occupied) {
				try {
					System.out.println(copyist.printIndex() + "is waiting for pencil.");
					wait();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			occupied = true;
		}

		public synchronized void putDown() {
			occupied = false;
			notify();// allow one of other threads waiting for this tool to
						// continue
		}

		// getter and setter
		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}

	class Eidographs {
		private int index;
		private boolean occupied = false;

		public Eidographs(int index) {
			this.index = index;
		}

		public synchronized void get() {
			Copyist copyist = (Copyist) Thread.currentThread();
			// get the copyist object that currently calling this method
			while (occupied) {
				try {
					System.out.println(copyist.printIndex() + "is waiting for eidograph.");
					wait();
				} catch (Exception e) {
					e.printStackTrace();
				}
				occupied = true;
			}
		}

		public synchronized void putDown() {
			occupied = false;
			notify();// allow one of other threads waiting for this tool to
						// continue
		}

		// getter and setter
		public int getIndex() {
			return index;
		}

		public void setIndex(int index) {
			this.index = index;
		}
	}
}
