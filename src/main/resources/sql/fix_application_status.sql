-- Fix legacy application status values (run once in pet_adoption database)
UPDATE application SET status = 'pending' WHERE status IN ('待审核') OR status = 'pending';
UPDATE application SET status = 'approved' WHERE status IN ('通过') OR status = 'approved';
UPDATE application SET status = 'rejected' WHERE status IN ('拒绝') OR status = 'rejected';
UPDATE application SET status = 'cancelled' WHERE status IN ('已取消') OR status = 'cancelled';
-- Convert unknown/garbled legacy values to pending so they can be reviewed again
UPDATE application SET status = 'pending'
WHERE status NOT IN ('pending', 'approved', 'rejected', 'cancelled');
