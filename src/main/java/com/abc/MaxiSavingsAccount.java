/**
 * 
 */
package com.abc;

import java.util.Date;
import java.util.Properties;

/**
 * A class representing a customer's maxi-savings account (<i>Account</i>
 * subclass)
 * 
 * @author Jeff
 * 
 */
public class MaxiSavingsAccount extends Account
	{

		private Date	last_withdraw_dt	= null;

		/**
		 * Create a new maxi savings account
		 */
		public MaxiSavingsAccount()
			{
				super(AccountType.MAXISAVINGS);
			}

		/**
		 * Calculate interest on a maxi-savings account
		 * 
		 * return the interest on the current balance for the current period.
		 * 
		 * @see com.abc.Account#calculateInterest()
		 */
		@Override
		public double calculateInterest()
			{
				String base_rate_str = this.getProperty("base_rate");
				String ext_rate_str = this.getProperty("ext_rate");
				String enh_rate_str = this.getProperty("enh_rate");

				// convert strings to actual rates
				double base_rate = Double.valueOf(base_rate_str);
				double ext_rate = Double.valueOf(ext_rate_str);
				double enh_rate = Double.valueOf(enh_rate_str);

				double interest = 0.00;

				// get the current balance
				double balance = getBalance(true);

				// first: if the amount is greater than 1000 apply the base rate to
				// the first 1000
				if (balance >= 1000)
					{
						interest = 1000 * base_rate;
					}
				
				if ( balance >= 2000 )
					{
						// apply the extended rate to the 2nd 1000
						interest += 1000 * ext_rate;
						
						// if the amount exceeds 3000, then apply the enhanced rate to the amount
						// over 2000
						interest += (balance - 2000) * enh_rate;	

					}
				else// we have in between 1000 and 2000, apply the extended rate on the amount
					 // over 1000
					{
						interest += (balance - 1000 ) * ext_rate; 
					}
				
				// return the interest
				return interest;
			}

		/**
		 * Withdraw an amount from the account
		 * 
		 * @see com.abc.Account#withdraw(double)
		 */
		@Override
		public Transaction withdraw(double _amount)
			{
				// make sure we call the super classes <i>withdraw</i> method
				Transaction t = super.withdraw(_amount);

				// update the last withdrawal date so we don't need to look it up
				this.last_withdraw_dt = t.getTransactionDate();

				// return the transaction 
				return t;
			}

		/**
		 * Reset to default maxi-account settings
		 * 
		 * @see com.abc.Account#resetProperties()
		 */
		@Override
		protected void resetProperties(Properties _p)
			{
				// clear all the current property entries
				_p.clear();

				// set the base rate
				_p.setProperty("base_rate", "0.02");
				// set the extended rate
				_p.setProperty("ext_rate", "0.05");
				// set the base rate
				_p.setProperty("enh_rate", "0.1");

			}

		/**
		 * Return the name of the properties file for Account::loadProperties.
		 * 
		 * @see com.abc.Account#getPropertiesFilename()
		 */
		@Override
		protected String getPropertiesFilename()
			{
				return "maxisavings.properties";
			}
	}
