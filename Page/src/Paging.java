import java.util.Scanner;

//Name:tao.ge
//ID:201218973
public class Paging {
	static int TIME = 0;
	static int PAGEFAULTS = 0;
	// construct 2 variables to record time and fault times
	static String title = "|Fr0\t|Fr1\t|Fr2\t|Fr3\t|Fr4\t|Fr5\t|Fr6\t|Fr7\t|Time\t|Pagefaults since last check\t|";
	// output text
	static int[] input = new int[20000];

	public static void main(String[] args) {
		System.out.println("Please input the data:");
		Scanner scanner = new Scanner(System.in);
		int mode = scanner.nextInt();
		// get the replace mode
		int count = 0;
//		while (scanner.hasNext() && count < 19999) {
//			input[count] = scanner.nextInt();
//			count++;
//		}
//		input[19999] = scanner.nextInt();
//		// get the page data
//		scanner.close();
//		Page[] pg = new Page[1024];
//		int[] store = new int[1024];
//		//construct the page and frame arrays
//		for (int i = 0; i < 1024; i++) {
//			pg[i] = new Page();
//			pg[i].LoadPage(i, TIME);
//			pg[i].Update(TIME);
//			store[i] = i;
//		}
		// construct and initialize page objects and store arrays
		switch (mode) {
		case 0:

			System.out.println("Find oldest is used");
			break;
		case 1:
			System.out.println("Find least recent is used");
			break;
		case 2:
			System.out.println("Find random is used");
			break;
		default:
			break;
		}
		// identify replacement mode
		System.out.println(title);
		for (int i : input) {
			if (pg[i].GetFrame() <= 7) {
				pg[i].Update(TIME);
//				// if the page exists in RAM, update it
//				TIME++;
//				if ((TIME % 100 == 0) && (TIME != 0)) {
//					for (int j = 0; j <= 7; j++) {
//						System.out.print("|" + store[j] + "\t");
//					}
//					System.out.println("|" + TIME + "\t|" + (PAGEFAULTS) + "\t\t|");
//					PAGEFAULTS = 0;
//				}
//			} else {
				// if the page is in disk
				int swap = 0;
				// set one variable to represent the frame number to be swapped
				PAGEFAULTS++;
				pg[i].Update(TIME);
				switch (mode) {
				case 0:

					swap = Page.Find_Oldest(store, pg);
					break;
				case 1:
					swap = Page.Find_LRU(store, pg);
					break;
				case 2:
					swap = Page.Find_Random();
					break;
				default:
					break;
				}
//				// select find mode
//				int temp = pg[i].GetFrame();
//				pg[i].LoadPage(swap, TIME);
//				pg[store[swap]].LoadPage(temp, TIME);
//				store[temp] = store[swap];
//				store[swap] = i;
//				// replacement algorithm
//				TIME++;
//				if ((TIME % 100 == 0) && (TIME != 0)) {
//					for (int j = 0; j <= 7; j++) {
//						System.out.print("|" + store[j] + "\t");
//					}
//					System.out.println("|" + TIME + "\t|" + (PAGEFAULTS) + "\t\t|");
//					PAGEFAULTS = 0;
//				}

			}
		}
	}

	public static class Page {
		private int current_frame;
		private int loaded_at;
		private int last_read;

		public Page() {
			this.current_frame = -1;
			this.loaded_at = -1;
			this.last_read = -1;
		}

		private int GetFrame() {
			return current_frame;
		}

		private int GetAge() {
			return loaded_at;
		}

		private int GetLastAccess() {
			return last_read;
		}

		private void LoadPage(int frame_value, int ClockTime) {
			if (frame_value > 7) {
				loaded_at = -1;
			} else {
				loaded_at = ClockTime;
			}
			this.current_frame = frame_value;
			// load method

		}

		private void Update(int ClockTime) {
			last_read = ClockTime;
		}

		public static int Find_Oldest(int[] Store, Page[] Table) {
			Page[] buffer = new Page[8];
			for (int i = 0; i < 8; i++) {
				buffer[i] = Table[Store[i]];
			}
			// construct one page list of 8 to fetch the 8 page object in RAM
			for (int i = 0; i < 8; i++) {
				if (buffer[0].GetAge() > buffer[i].GetAge()) {
					buffer[0] = buffer[i];
				}
				// compare with each other to get the oldest object
			}
			return buffer[0].GetFrame();

		}

		public static int Find_LRU(int[] Store, Page[] Table) {
			Page[] buffer = new Page[8];
			for (int i = 0; i < 8; i++) {
				buffer[i] = Table[Store[i]];
			}
			// construct one page list of 8 to fetch the 8 page object in RAM
			for (int i = 0; i < 8; i++) {
				if (buffer[0].GetLastAccess() > buffer[i].GetLastAccess()) {
					buffer[0] = buffer[i];
				}
				// compare with each other to get the least recent used object

			}
			return buffer[0].GetFrame();
		}

		public static int Find_Random() {
			return (int) (Math.random() * 8);
			// get random index
		}
	}

}
