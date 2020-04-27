#!/usr/bin/env python

## generates mock click logs data
## log format
##   timestamp (in ms), ip_address, user, action, domain, campaign_id, cost, session_id

## timestamp converstions testing site : http://www.epochconverter.com/


## ----- config
days=10
entries_per_day=100000
# file size calculations
#  format     entries_per_day     file_size in MB
#  csv       10000000             830 M file
#  json      1000000              169 M
#  json      3200000              500 M

log_format="csv"
#log_format="json"
## --- end config


import os
import datetime as dt
import random
import json

domains = ['facebook.com',  'yahoo.com',   'google.com',   'zynga.com',    'wikipedia.org',   'sf.craigslist.org',   'twitter.com',    'amazon.com',    'flickr.com',    'cnn.com',      'usatoday.com',      'npr.org',    'foxnews.com',    'comedycentral.com',   'youtube.com',   'hulu.com',   'bbc.co.uk',  'nytimes.com',   'sfgate.com',   'funnyordie.com']

actions = ['viewed', 'clicked', 'blocked']
total_users = 100000
total_ips = 1000
total_sessions = 100
total_campaigns = 20

# overwrite this function to customize log generation
def generate_log(timestamp):
  user = "user_%d" % random.randint(1,total_users)
  action = random.choice(actions)
  domain = random.choice(domains)
  ip_address = "ip_%d" % random.randint(1,total_ips)
  campaign = "campaign_%d" % random.randint(1,total_campaigns)
  session = "session_%s" % random.randint(1,total_sessions)
  #cost is in cents, could be zero
  cost = random.randint(1,200) - 20
  if (cost < 0):
    cost = 0

  #csv
  if (log_format == 'csv'):
    logline = "%s,%s,%s,%s,%s,%s,%s,%s" % (timestamp, ip_address, user, action, domain, campaign, cost, session)

  # generate JSON format
  if (log_format == 'json'):
    dict={'timestamp': timestamp, 'ip': ip_address, 'user': user, 'action': action, 'domain': domain,  'campaign':campaign, 'cost': cost,  'session': session}
    logline = json.dumps(dict)


  #print logline
  return logline



#main
## --- script main
if __name__ == '__main__':
  time_inc_ms = int ((24.0*3600*1000)/entries_per_day)
  #print "time inc ms", time_inc_ms
  #epoch = dt.datetime.fromtimestamp(0)
  epoch = dt.datetime(1970,1,1)

  year_start = dt.datetime(2015, 1, 1)
  for day in range(0, days):
    day_delta = dt.timedelta(days=day)
    start_ts = year_start + day_delta
    #end_ts = dt.datetime(start_ts.year, start_ts.month, start_ts.day, 23, 59, 59)
    end_ts = dt.datetime(start_ts.year, start_ts.month, start_ts.day+1, 0, 0, 0)
    filename = "clickstream-" + start_ts.strftime("%Y-%m-%d") 
    if (log_format == 'csv'):
      filename = filename + ".csv"
    if (log_format == 'json'):
      filename = filename + ".json"

    #print start_ts
    #print end_ts
    last_ts = start_ts

    with open(filename, "w") as fout:
      print "generating log ", filename
      while (last_ts < end_ts):
        delta_since_epoch = last_ts - epoch
        millis = int((delta_since_epoch.microseconds + (delta_since_epoch.seconds + delta_since_epoch.days * 24 * 3600) * 10**6) / 1e3)
        #print "last ts", last_ts
        #print "millis",  millis
        logline = generate_log(millis)
        fout.write(logline + "\n")

        last_ts = last_ts + dt.timedelta(milliseconds=time_inc_ms)

