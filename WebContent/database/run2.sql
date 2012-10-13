UPDATE item SET firstBid = (firstBid * 100);
UPDATE item SET minBid = (minBid * 100);
UPDATE item SET minBid = firstBid WHERE (minBid > firstBid);
UPDATE user SET password = "18FDFF8D3682ABE4F3EF8C60B2FE98DEBC29712A";
UPDATE user SET userdetails = id;