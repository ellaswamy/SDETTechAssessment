package api_tests;

import org.apache.logging.log4j.*;

public class RateMovie {
		
	private static Logger log=LogManager.getLogger(RateMovie.class.getName());
		double value;
		public double getValue()
		{
			log.info("reading Rating value of a movie");
			return value;
		}
		public void setValue(double rating)
		{
			log.info("Setting Rating value of a movie with "+String.valueOf(rating));
			value=rating;
		}
}
