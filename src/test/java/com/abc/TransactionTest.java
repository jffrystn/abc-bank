package com.abc;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class TransactionTest
	{

		@Test
		public void testCreate()
			{
				Transaction t = new DepositTransaction(5);
				assertTrue(t instanceof Transaction);
			}

	}
