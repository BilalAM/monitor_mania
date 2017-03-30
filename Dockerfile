FROM sebp/elk

EXPOSE 5601 9200 9300 5044

CMD [ "/usr/local/bin/start.sh" ]
