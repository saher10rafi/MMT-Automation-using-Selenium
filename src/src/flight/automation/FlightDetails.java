package flight.automation;

public class FlightDetails {
	
		public String Name;
		public long Price;
		public String DeptTime;	

		public String getDeptTime() {
			return DeptTime;
		}

		public void setDeptTime(String deptTime) {
			DeptTime = deptTime;
		}

		public String getName() {
			return Name;
		}

		public void setName(String name) {
			Name = name;
		}

		public long getPrice() {
			return Price;
		}

		public void setPrice(long price) {
			Price = price;
		}

	}
