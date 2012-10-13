UPDATE item SET firstBid = (firstBid * 100);
UPDATE item SET minBid = (minBid * 100);
UPDATE item SET minBid = firstBid WHERE (minBid > firstBid);