(ns runbld.schema
  (:require [schema.core :as s])
  (:import (elasticsearch.connection Connection)))

(def OptsEmail
  {(s/required-key :from              ) s/Str
   (s/required-key :host              ) s/Str
   (s/required-key :max-failure-notify) s/Num
   (s/required-key :pass              ) s/Str
   (s/required-key :port              ) (s/cond-pre s/Num s/Str)
   (s/required-key :template-html     ) (s/cond-pre s/Str java.io.File)
   (s/required-key :template-txt      ) (s/cond-pre s/Str java.io.File)
   (s/required-key :text-only         ) s/Bool
   (s/required-key :tls               ) s/Bool
   (s/required-key :to                ) (s/cond-pre s/Str [s/Str])
   (s/required-key :user              ) s/Str})

(def OptsProcess
  {(s/required-key :program   ) s/Str
   (s/required-key :args      ) [s/Str]
   (s/required-key :cwd       ) s/Str
   (s/required-key :scriptfile) s/Str})

(def OptsElasticsearch
  {(s/required-key :index    ) s/Str
   (s/required-key :conn     ) Connection
   (s/optional-key :http-opts) {s/Keyword s/Any}})

(def OptsS3
  {(s/required-key :access-key) s/Str
   (s/required-key :secret-key) s/Str
   (s/required-key :bucket    ) s/Str
   (s/required-key :prefix    ) s/Str})

(def Opts
  {(s/required-key :job-name    ) s/Str
   (s/required-key :version     ) (s/maybe s/Bool)
   (s/required-key :configfile  ) (s/maybe s/Str)
   (s/required-key :email       ) OptsEmail
   (s/required-key :es          ) OptsElasticsearch
   (s/required-key :process     ) OptsProcess
   (s/required-key :s3          ) OptsS3})

(def BuildSystem
  {(s/required-key :arch           ) s/Str
   (s/required-key :cpu-type       ) s/Str
   (s/required-key :cpus           ) s/Num
   (s/required-key :cpus-physical  ) s/Num
   (s/required-key :hostname       ) s/Str
   (s/required-key :ipv4           ) s/Str
   (s/required-key :ipv6           ) s/Str
   (s/required-key :kernel-release ) s/Str
   (s/required-key :kernel-version ) s/Str
   (s/required-key :model          ) s/Str
   (s/required-key :os             ) s/Str
   (s/required-key :os-version     ) s/Str
   (s/required-key :ram-mb         ) s/Num
   (s/required-key :timezone       ) s/Str
   (s/required-key :uptime-secs    ) s/Int
   (s/required-key :virtual        ) s/Bool})

(def Env
  {s/Str s/Any})

(def Opts2
  (merge Opts {(s/required-key :sys) BuildSystem}))

(def Opts3
  (merge Opts2 {(s/required-key :env) Env}))

(def Opts4
  (merge Opts3 {(s/required-key :id) s/Str
                (s/required-key :build) {s/Keyword s/Any}
                (s/required-key :jenkins) {s/Keyword s/Any}}))

(def OptsFinal
  (merge Opts4 {(s/required-key :vcs) {s/Keyword s/Any}}))

(def ProcessResult
  {(s/required-key :cmd            ) [s/Str]
   (s/required-key :cmd-source     ) s/Str
   (s/required-key :err-accuracy   ) s/Int
   (s/required-key :err-bytes      ) s/Num
   (s/required-key :err-file       ) s/Str
   (s/required-key :err-file-bytes ) s/Int
   (s/required-key :exit-code      ) s/Num
   (s/required-key :millis-end     ) s/Num
   (s/required-key :millis-start   ) s/Num
   (s/required-key :out-accuracy   ) s/Int
   (s/required-key :out-bytes      ) s/Num
   (s/required-key :out-file       ) s/Str
   (s/required-key :out-file-bytes ) s/Int
   (s/required-key :status         ) s/Str
   (s/required-key :time-end       ) s/Str
   (s/required-key :time-start     ) s/Str
   (s/required-key :took           ) s/Num})

(def VcsLog
  {(s/required-key :commit-id     ) s/Str
   (s/required-key :message-short ) s/Str
   (s/required-key :message-full  ) s/Str
   (s/required-key :commit-name   ) s/Str
   (s/required-key :commit-email  ) s/Str
   (s/required-key :commit-time   ) s/Str
   (s/required-key :author-name   ) s/Str
   (s/required-key :author-email  ) s/Str
   (s/required-key :author-time   ) s/Str})

(def StoredBuild
  {(s/required-key :id     ) s/Str
   (s/required-key :sys    ) BuildSystem
   (s/required-key :vcs    ) VcsLog
   (s/required-key :jenkins) java.util.Map
   (s/required-key :process) ProcessResult})