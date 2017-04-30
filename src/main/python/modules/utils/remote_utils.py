import requests
import logging

REMOTE_USER = ''
REMOTE_PASS = ''
REMOTE_IP = 'http://localhost:8000'

logger = logging.getLogger('monitor_stats')


def send(post_data):
    logger.debug('Sending data to remote api.' + str(post_data))
    ret_code = -1
    try:
        r = requests.post(REMOTE_IP, auth=(REMOTE_USER, REMOTE_PASS), data=post_data)
        ret_code = r.status_code
        if ret_code == 200:
            logger.info('Sent data to remote api ' + str(len(post_data)))
    except Exception as e:
        logger.error('Failed to send data to remote api\n' + str(e))

    return ret_code
