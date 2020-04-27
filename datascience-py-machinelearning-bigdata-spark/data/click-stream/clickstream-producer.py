#!/usr/bin/env python

## generates mock click logs data
## log format
##   timestamp (in ms), ip_address, user, action, domain, campaign_id, cost, session_id

## timestamp converstions testing site : http://www.epochconverter.com/

## By default it prints to console (stdout)
## to redirect to a file:
##    $   python clickstream-producer.py   |  tee output_file
##
## to redirect to a network socket
##    $  python clickstream-producer.pyt   | nc localhost 10000
#  Here netcat is intercepting the program output and feeding into port 10000
## To listen to these events we can use netcat on another console like this
##    $  nc   -lk   10000


## ----- config
events_per_second=1
log_format="csv"
#log_format="json"
## --- end config


import os
import datetime
import time
import random
import json
import sys

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
  
  events = 0
  while True:
    events = events + 1
    timestamp_ms = int(time.time()*1000)
    logline = generate_log(timestamp_ms)
    print(logline)
    if (events >= events_per_second):
      sys.stdout.flush()
      events = 0
      time.sleep(1)


