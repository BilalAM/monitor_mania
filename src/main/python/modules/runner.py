import time
import logging
from utils import cmd_utils
from utils import remote_utils
from utils import result_processor

logger = logging.getLogger('monitor_stats')


def main():
    logger.info('Starting monitor at ' + str(time.time()))
    # step 1, fire top command

    t_end = time.time() + 60 * 2
    while time.time() < t_end:
        cmd_output = cmd_utils.run_cmd('top', ['-l', '1'])

        # step 1b, process the output
        processed_result = result_processor.process(cmd_output)

        # step 2, send output to remote system
        remote_utils.send(processed_result)


if __name__ == '__main__':
    main()
