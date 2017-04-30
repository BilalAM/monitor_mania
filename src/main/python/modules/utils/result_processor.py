import json

import logging

logger = logging.getLogger('monitor_stats')


def process(cmd_output):
    json_data = ''
    cmd_output = str(cmd_output)
    try:
        json_data = json.dumps(cmd_output)
    except Exception as e:
        logger.exception(e)

    return {
        'data': json_data
    }
