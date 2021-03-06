# top-level Eucalyptus makefile
#
#

include Makedefs

# notes: storage has to preceed node and node has to preceed cluster
SUBDIRS			=	tools \
				util \
				net \
				storage	 \
				gatherlog \
				node  \
				cluster \
			        clc

INDENTDIRS =	cluster \
				util \
				net \
				storage	 \
				gatherlog \
				node \
				clc

.PHONY: all clean distclean build 

all: build

help:
	@echo; echo "Available targets:"
	@echo "   all          this is the default target: it builds eucalyptus"
	@echo "   install      install eucalyptus"
	@echo "   clean        remove objects file and compile by-products"
	@echo "   distclean    restore the source tree to a pristine state"
	@echo 


tags:
	@echo making tags for emacs and vi
	find cluster net node storage tools util -name "*.[chCH]" -print | xargs ctags 
	find cluster net node storage tools util -name "*.[chCH]" -print | xargs etags

build: Makedefs 
	@for subdir in $(SUBDIRS); do \
		(cd $$subdir && $(MAKE) $@) || exit $$? ; done

doc:
	@(cd doxygen && $(MAKE) $@) || exit $$? ;

deploy: build
	@for subdir in $(SUBDIRS); do \
		(cd $$subdir && $(MAKE) $@) || exit $$? ; done

install: deploy
	@$(INSTALL) -d $(DESTDIR)$(prefix)
	@$(INSTALL) -d $(DESTDIR)$(etcdir)/eucalyptus/cloud.d
	@$(INSTALL) -d $(DESTDIR)$(etcdir)/eucalyptus/nc-hooks
	@$(INSTALL) -m 0644 VERSION $(DESTDIR)$(etcdir)/eucalyptus/eucalyptus-version
	@$(INSTALL) -d $(DESTDIR)$(etcdir)/init.d
	@$(INSTALL) -d $(DESTDIR)$(etcdir)/logrotate.d
	@$(INSTALL) -d $(DESTDIR)$(vardir)/run/eucalyptus/net
	@$(INSTALL) -d $(DESTDIR)$(vardir)/lib/eucalyptus/keys
	@$(INSTALL) -d $(DESTDIR)$(vardir)/lib/eucalyptus/CC
	@$(INSTALL) -d $(DESTDIR)$(vardir)/log/eucalyptus
	@$(INSTALL) -d $(DESTDIR)$(datarootdir)/eucalyptus
	@$(INSTALL) -d $(DESTDIR)$(datarootdir)/eucalyptus/doc
	@$(INSTALL) -d $(DESTDIR)$(usrdir)/sbin
	@$(INSTALL) -d $(DESTDIR)$(usrdir)/lib/eucalyptus
	@$(INSTALL) -d $(DESTDIR)$(etcdir)/bash_completion.d
	@$(INSTALL) -d $(DESTDIR)$(libexecdir)/eucalyptus
	@for subdir in $(SUBDIRS); do \
		(cd $$subdir && $(MAKE) $@) || exit $$? ; done

uninstall:
	@$(RM) -f $(DESTDIR)$(etcdir)/eucalyptus/eucalyptus-version
	@for subdir in $(SUBDIRS); do \
		(cd $$subdir && $(MAKE) $@) || exit $$? ; done

indent: Makedefs
	@for subdir in $(INDENTDIRS); do \
		(cd $$subdir && $(MAKE) $@) || exit $$? ; done

clean:
	@for subdir in $(SUBDIRS); do \
		(cd $$subdir && $(MAKE) $@) || exit $$? ; done
	@rm -f tags TAGS

distclean: clean
	@for subdir in $(SUBDIRS); do \
		(cd $$subdir && $(MAKE) $@) || exit $$? ; done
	@rm -f config.cache config.log config.status Makedefs tags TAGS
	@# they where part of CLEAN
	@rm -rf lib 

Makedefs: Makedefs.in config.status
	./config.status

config.status: configure
	@if test ! -x ./config.status; then \
		echo "you have to run ./configure!"; exit 1; fi
	./config.status --recheck

# DO NOT DELETE
