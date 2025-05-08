package org.gestiondestitresimportationbcp.events;

import org.springframework.context.ApplicationEvent;

import java.io.File;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

public class FileCreatedEvent extends ApplicationEvent {
        private final File file;
        private Date dateReception;

        public FileCreatedEvent(Object source, File file, Date dateReception) {
            super(source);
            this.file = file;
            this.dateReception = dateReception;
        }
        public File getFile() {
            return file;
        }
        public Date getDateReception() {
            return dateReception;
        }

}
