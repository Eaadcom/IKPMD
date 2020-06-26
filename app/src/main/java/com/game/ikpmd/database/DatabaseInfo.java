package com.game.ikpmd.database;

public class DatabaseInfo {

    public class AppTables {
        public static final String CITYTABLE = "CityTable";
        public static final String ATTACKTABLE = "AttackTable";
    }

    public class CityColumn {
        public static final String UniqueIdentifier = "uniqueIdentifier";
        public static final String Owner = "owner";
        public static final String Name = "name";
        public static final String XAxisPosition = "xAxisPosition";
        public static final String YAxisPosition = "yAxisPosition";
        public static final String Swordsman = "swordsman";
        public static final String Archer = "archer";
        public static final String Horseman = "horseman";
    }

    public class AttackColumn {
        public static final String TargetCityUniqueId = "targetCityUniqueId";
        public static final String OriginCityUniqueId = "originCityUniqueId";
        public static final String ASwordsman = "aSwordsman";
        public static final String AArcher = "aArcher";
        public static final String AHorseman = "aHorseman";
        public static final String Arrivaltime = "arrivaltime";
        public static final String UniqueAttackIdentifier = "uniqueAttackIdentifier";
        public static final String Arrived = "arrived";
    }
}
